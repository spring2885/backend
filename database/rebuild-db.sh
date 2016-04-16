#!/bin/bash
set -e
echo "Recreating the backend schema."

mysql -ubackend  -pbackend -Dbackend < backend.sql
mysql -ubackend  -pbackend -Dbackend < backend-test-data.sql 

echo "Done!"