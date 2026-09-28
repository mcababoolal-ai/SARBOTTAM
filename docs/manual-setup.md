# Manual setup required from you

## Local development

1. Install Docker Desktop, Java 21, Maven, and Node.js 20 or newer.
2. In Keycloak (`http://localhost:8081`, `admin` / `admin`), create realm `school`.
3. Create roles such as `SCHOOL_ADMIN`, `ADMISSION_OFFICER`, and `TEACHER`, then create a test user.
4. Add a mapper for a `school_id` user attribute so the access token contains a UUID claim named `school_id`.
5. Configure the client used by React as a public client with redirect URL `http://localhost:5173/*`.

## Before AWS deployment

You must supply/control these account-bound items:

- AWS account, billing, domain name, and a least-privilege deployment IAM role.
- EKS, RDS, MSK, ECR, S3, Route 53, ACM, and Secrets Manager provisioning credentials; use Terraform instead of clicking through the console.
- A payment-gateway merchant account and webhook secret.
- SMS/WhatsApp/email provider identities, API keys, and any required legal registration.
- Privacy policy, data-retention policy, backup retention period, and school data-import files.

Never commit these values. Store environment-specific credentials in AWS Secrets Manager and expose them through External Secrets Operator in Kubernetes.
