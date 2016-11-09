# Requirements

## Libvirt setup

Create a storage pool:

```
cat > /etc/libvirt/storage/domjudge.xml
<pool type='dir'>
  <name>domjudge</name>
  <uuid>c64f38db-4187-4f8b-3d3a-8251ef4e2246</uuid>
  <capacity unit='bytes'>1968740425728</capacity>
  <allocation unit='bytes'>488388210688</allocation>
  <available unit='bytes'>1480352215040</available>
  <source>
  </source>
  <target>
    <path>/bigdata/vms/virtimages/domjudge</path>
    <permissions>
      <mode>0755</mode>
      <owner>-1</owner>
      <group>-1</group>
    </permissions>
  </target>
</pool>
```

Then,

```
sudo systemctl restart libvirtd
```

## Vagrant plugins

### Vagrant libvirt

- `vagrant plugin install vagrant-libvirt`


### Vagrant mutate

Perform these steps on a machine with a modern qemu. We'll need to convert the modern qemu disk image to an older format before using on RHEL 6.4.

Install the Vagrant mutate plugin:

`vagrant plugin install vagrant-mutate`

Then mutate the typical Ubuntu Trusty64 image to qemu (libvirt) format:

```
vagrant mutate http://cloud-images.ubuntu.com/vagrant/trusty/current/trusty-server-cloudimg-amd64-vagrant-disk1.box libvirt
```

Now convert the image to an older-format image:

`
qemu-img convert -o compat=0.10 -f qcow2 -O qcow2 -c -p box.img  box.img.compat0.10
`

Remove sata option from Vagrantfile.

Fix the permissions for ssh:

```
guestfish --rw -a box.img
><fs> run
><fs> mount /dev/sda1 /
><fs> chmod 0700 /home/vagrant/.ssh
><fs> chmod 0600 /home/vagrant/.ssh/authorized_keys
><fs> umount /
><fs> quit
```

Package up the files into a Vagrant box:

```
tar czvf trusty64.box ./metadata.json ./Vagrantfile ./box.img
```

Install the box into the Vagrant registry:

```
vagrant box add --name stetson-domjudge/trusty64 --provider libvirt trusty64.box
```

### Vagrant host manager

Install Vagrant hostmanager:

`vagrant plugin install vagrant-hostmanager`

On the host machine, set up sudo access for the Vagrant hostmanager:

```
Cmnd_Alias VAGRANT_HOSTMANAGER_UPDATE = /bin/cp /home/user/.vagrant.d/tmp/hosts.local /etc/hosts
%sudo ALL=(root) NOPASSWD: VAGRANT_HOSTMANAGER_UPDATE
```

## Setup

- `export VAGRANT_DEFAULT_PROVIDER=libvirt`
- `vagrant up --no-parallel`

