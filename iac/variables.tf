variable "environment" {
  description = "O ambiente de implantação (ex: dev, prod)"
  type        = string
  default     = "dev"
}

variable "location" {
  description = "A localização do data center do Azure (ex: westus)"
  type        = string
  default     = "eastus"
}