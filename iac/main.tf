resource "azurerm_resource_group" "az_rg" {
  name     = "rg-${var.environment}-cosmosdb-${var.location}"
  location = var.location
}

resource "random_integer" "ri" {
  min = 10000
  max = 99999
}

resource "azurerm_cosmosdb_account" "cosmos_001" {
  name                = "acc-${var.environment}-cosmosdb-${var.location}-${random_integer.ri.result}"
  resource_group_name = azurerm_resource_group.az_rg.name
  location            = azurerm_resource_group.az_rg.location
  offer_type          = "Standard"
  kind                = "MongoDB"
  enable_free_tier    = true

  capabilities {
    name = "EnableMongo"
  }

  consistency_policy {
    consistency_level       = "Session"
  }

  geo_location {
    location          = azurerm_resource_group.az_rg.location
    failover_priority = 0
  }

  tags = {
    environment = var.environment
  }
}

resource "azurerm_redis_cache" "redis_001" {
  name                = "redis-${var.environment}-${var.location}-${random_integer.ri.result}"
  location            = azurerm_resource_group.az_rg.location
  resource_group_name = azurerm_resource_group.az_rg.name
  capacity            = 0  # Basic Tier, smallest size
  family              = "C" # Basic Tier
  sku_name            = "Basic" # Basic Tier
  enable_non_ssl_port = false
  minimum_tls_version = "1.2"

  redis_configuration {}

  tags = {
    environment = var.environment
  }
}

resource "azurerm_service_plan" "example" {
  name                = "asp-${var.environment}-${var.location}-${random_integer.ri.result}"
  location            = azurerm_resource_group.az_rg.location
  resource_group_name = azurerm_resource_group.az_rg.name
  os_type             = "Linux"
  sku_name            = "B1"
}


resource "azurerm_linux_web_app" "example_app_service" {
  name                = "app-${var.environment}-${var.location}-${random_integer.ri.result}"
  resource_group_name = azurerm_resource_group.az_rg.name
  location            = azurerm_resource_group.az_rg.location
  service_plan_id     = azurerm_service_plan.example.id

  app_settings = {
    "WEBSITES_ENABLE_APP_SERVICE_STORAGE" = "false"
    "MONGODB_CONNECTION_STRING"           = azurerm_cosmosdb_account.cosmos_001.connection_strings[0]
    "REDIS_HOSTS"                         = "rediss://default@${azurerm_redis_cache.redis_001.hostname}:${azurerm_redis_cache.redis_001.ssl_port}"
    "REDIS_PASSWORD"                      = azurerm_redis_cache.redis_001.primary_access_key
  }
  site_config  {
    application_stack  {
      docker_registry_url = "https://registry.hub.docker.com"
      docker_image_name  = "joaomilhome/poc-xpe-desafio-02-jvm:latest"
    }
  }



  https_only = true

  tags = {
    environment = var.environment
  }
}
