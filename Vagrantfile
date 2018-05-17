# -*- mode: ruby -*-
# vi: set ft=ruby :

$script = <<SCRIPT
apt-get update
# Install debconf-utils
sudo apt-get install -y debconf-utils
# Install mysql-server
sudo debconf-set-selections <<< "mysql-server mysql-server/root_password password password"
sudo debconf-set-selections <<< "mysql-server mysql-server/root_password_again password password"
sudo apt-get install -y mysql-server
echo 'Setup Mysql'
mysql -uroot -ppassword -e "grant all privileges on *.* to 'moco'@'%' identified by 'moco' with grant option;"
mysql -uroot -ppassword -e "create database moco_test;"
mysql -uroot -ppassword -e "create database moco_sample;"
mysql -uroot -ppassword -e "create database moco_develop;"

mysql -uroot -ppassword moco_test < /vagrant/scripts/schema.sql
mysql -uroot -ppassword moco_develop < /vagrant/scripts/schema.sql

mysql -uroot -ppassword moco_sample < /vagrant/scripts/schema_sample.sql

sed -i "s/.*bind-address.*/bind-address = 0.0.0.0/" /etc/mysql/my.cnf
sed -i "119 a default-character-set=utf8mb4" /etc/mysql/my.cnf
sed -i "31 a character-set-server=utf8mb4" /etc/mysql/my.cnf
sudo service mysql restart
SCRIPT

Vagrant.configure(2) do |config|
  config.vm.box = "debian/jessie64"

  config.vm.provider "virtualbox" do |vb|
    vb.memory = "1024"
  end

  config.vm.network "forwarded_port", guest: 3306, host: 3306
  config.vm.provision "shell", inline: $script
end