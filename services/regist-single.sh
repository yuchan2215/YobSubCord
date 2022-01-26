#!/bin/bash

sudo cp bot-single.service /etc/systemd/system/bot.service
sudo systemctl daemon-reload