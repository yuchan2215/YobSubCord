#!/bin/bash

cd `dirname $0`
sudo cp ./services/bot.service /etc/systemd/system/bot.service
sudo systemctl daemon-reload