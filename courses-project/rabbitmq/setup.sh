#!/bin/bash
sleep 10  # Wait for RabbitMQ to start

# Create a new user with permissions
rabbitmqctl add_user admin admin || true  # Skip if the user already exists
rabbitmqctl set_permissions -p / admin ".*" ".*" ".*"  # Full permissions for the user
rabbitmqctl set_user_tags admin administrator  # Optionally, set user as administrator
