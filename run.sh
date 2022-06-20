#!/bin/bash
docker build . -t winewebshop
docker run --rm -it --name webshopapp -e DATABASE_USERNAME=admin -e DATABASE_PASSWORD=admin -e DATABASE_URL=jdbc:h2:mem:winewebshop -p 8080:8080 winewebshop