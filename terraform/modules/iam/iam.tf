variable "service_name" {
  description = "Name of the microservice"
  type        = string
}

resource "aws_iam_role" "service_role" {
  name               = "${var.service_name}-role"
  assume_role_policy = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Action": "sts:AssumeRole",
      "Principal": { "Service": "ecs-tasks.amazonaws.com" },
      "Effect": "Allow",
      "Sid": ""
    }
  ]
}
EOF
}

output "iam_role_name" {
  value = aws_iam_role.service_role.name
}
