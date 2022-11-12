#!/bin/sh

docker run --rm -it --network="host" -e POSTGRES_PASSWORD=postgres postgres