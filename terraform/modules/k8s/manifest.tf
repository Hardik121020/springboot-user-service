variable "service_name" {
  description = "Name of the microservice"
  type        = string
}

resource "local_file" "deployment_manifest" {
  content = <<EOF
apiVersion: apps/v1
kind: Deployment
metadata:
  name: ${var.service_name}
spec:
  replicas: 2
  selector:
    matchLabels:
      app: ${var.service_name}
  template:
    metadata:
      labels:
        app: ${var.service_name}
    spec:
      containers:
      - name: ${var.service_name}
        image: <ECR_IMAGE_PLACEHOLDER>
        ports:
        - containerPort: 8080
EOF
  filename = "${path.module}/${var.service_name}-deployment.yaml"
}

output "k8s_manifest_path" {
  value = local_file.deployment_manifest.filename
}
