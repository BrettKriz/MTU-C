# -*- mode: ruby -*-
# vi: set ft=ruby :

Vagrant::Config.run do |config|
  # Every Vagrant virtual environment requires a box to build off of.
  config.vm.box = "contest-ubuntu-server"
  config.vm.provision :shell, :inline => "git clone https://jrkettle@bitbucket.org/jrkettle/2014-mtu-sample-clients.git /vagrant/contest-bots"
  config.vm.box_url = "http://www1.contest/static/package.box"
  config.vm.boot_mode = :gui
end
