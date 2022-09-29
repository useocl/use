---
name: Update USE version
about: Template to update version after a release
title: 'Update USE version after release of ${{env.OLD_USE_VERSION}}'
assignees: ''
---

## Description

After the release of USE version ${{env.OLD_USE_VERSION}} it is tiome to change
the version to a new one. For this, please change the following files:

* ./pom.xml
* ./use-assembly/pom.xml
* ./use-core/pom.xml
* ./use-core/src/main/java/org/tzi/use/config/Options.java
* ./use-gui/pom.xml
