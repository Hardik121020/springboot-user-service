variable "service_name" {
  description = "Name of the microservice"
  type        = string
}

resource "aws_ecr_repository" "service_repo" {
  name = var.service_name
}

output "ecr_repo_url" {
  value = aws_ecr_repository.service_repo.repository_url
}
