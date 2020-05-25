#!/bin/bash

# install NuSMV
# assuming 64 bit linux
if [[ $(uname -i) == "x86_64" ]]
then
    nusmv_url=http://nusmv.fbk.eu/distrib/NuSMV-2.6.0-linux64.tar.gz
else
    nusmv_url=http://nusmv.fbk.eu/distrib/NuSMV-2.6.0-linux32.tar.gz
fi
    nusmv_archive=${nusmv_url##*/}
if [[ ! -r $nusmv_archive ]]
then
      curl -fsSLO $nusmv_url
fi
pushd /usr/local
sudo tar --strip-components 1 -xf $(dirs -l +1)/$nusmv_archive
popd

