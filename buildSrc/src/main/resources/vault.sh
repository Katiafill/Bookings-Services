vault secrets enable -path=bookings-services -version=2 kv
vault kv put bookings-services/application  spring.datasource.username=postgres spring.datasource.password=postgres