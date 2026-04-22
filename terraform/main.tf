module "ecr" {
  source       = "./modules/ecr"
  service_name = var.service_name
}

module "iam" {
  source       = "./modules/iam"
  service_name = var.service_name
  team_name    = var.team_name
}

module "k8s" {
  source       = "./modules/k8s"
  service_name = var.service_name
  repo_url     = var.repo_url
}
