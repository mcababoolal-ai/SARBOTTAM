import './auth.css';
import Keycloak from 'keycloak-js';

export const authEnabled = import.meta.env.VITE_AUTH_ENABLED === 'true';
export const keycloak = new Keycloak({ url: import.meta.env.VITE_KEYCLOAK_URL ?? 'http://localhost:8081', realm: import.meta.env.VITE_KEYCLOAK_REALM ?? 'school', clientId: import.meta.env.VITE_KEYCLOAK_CLIENT_ID ?? 'school-web' });
export async function apiFetch(path:string, options:RequestInit={}) { const schoolId=authEnabled ? String((keycloak.tokenParsed as Record<string,unknown>|undefined)?.school_id??'') : (import.meta.env.VITE_SCHOOL_ID??'00000000-0000-0000-0000-000000000001'); if(authEnabled&&keycloak.authenticated)await keycloak.updateToken(30);const headers=new Headers(options.headers);headers.set('X-School-Id',schoolId);if(options.body)headers.set('Content-Type','application/json');if(authEnabled&&keycloak.token)headers.set('Authorization',`Bearer ${keycloak.token}`);return fetch(path,{...options,headers});}
export function currentRoles():string[]{return ((keycloak.tokenParsed as {realm_access?:{roles?:string[]}}|undefined)?.realm_access?.roles)??[];}
