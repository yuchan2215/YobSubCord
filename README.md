# シングルコア環境での注意点
シングルコアのCPU上だと正常に動作しないことが確認されています。 #1

[single-core.sh](single-core.sh)を利用することで正常に実行することができます。
```
sh single-core.sh jarのパス
```

# ENVファイルの作成
次の内容でenvファイル(.env)を作成します。
```dotenv
DISCORDTOKEN= # TOKEN HERE #
ADMINROLE= # 管理者のロールid #
ALERTROLE= # チャンネル内でメンションするロールID #
DMALERTROLE= # DMで通知するロールID 空白で無効 #
ALERTCHANNEL = # 通知するDiscordチャンネルID #
```

# LICENSES
[このプロジェクトのライセンス](LICENSE)
 - jackson-module-kotlin
   - [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)
 - jackson-module-kotlin
   - [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)
 - kotlin-reflect
   - [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)
 - kotlin-stdlib-jdk8
   - [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)
 - spring-boot-starter-test
   - [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)
 - gradle
   - [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)
 - dependency-management-plugin
   - [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)
 - dotenv-kotlin
   - [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)
 - JDA
   - [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)
 - Git-Version Gradle Plugin
   - [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)
 - Apache Groovy
   - [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0)
 - JDK17
   - [Oracle No-Fee Terms and Conditions (NFTC)](https://www.oracle.com/downloads/licenses/no-fee-license.html)