#!/bin/bash
cd `dirname $0`
sudo cp bot-multi.service /etc/systemd/system/bot.service
sudo systemctl daemon-reload