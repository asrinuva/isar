#
# Copyright (c) Siemens AG, 2026
#
# SPDX-License-Identifier: MIT

inherit trusted-firmware-a

MAINTAINER = "isar-users <isar-users@googlegroups.com>"

SRC_URI += "https://github.com/ARM-software/arm-trusted-firmware/archive/v${PV}.tar.gz;downloadfilename=arm-trusted-firmware-${PV}.tar.gz"
SRC_URI[sha256sum] = "2e18b881ada9198173238cca80086c787b1fa3f698944bde1743142823fc511c"

S = "${WORKDIR}/arm-trusted-firmware-${PV}"
CHANGELOG_V = "${PV}+${PR}"

TF_A_NAME = "imx8mmlpd4-evk"
TF_A_PLATFORM = "imx8mm"
TF_A_BINARIES = "release/bl31.bin"

COMPATIBLE_MACHINE = "^(imx8mmlpd4-evk)$"

do_deploy[dirs] = "${DEPLOY_DIR_IMAGE}"
do_deploy() {
	dpkg --fsys-tarfile ${WORKDIR}/${PN}_${CHANGELOG_V}_${DISTRO_ARCH}.deb | \
		tar xOf - ./usr/lib/trusted-firmware-a/${TF_A_NAME}/bl31.bin \
		> ${DEPLOY_DIR_IMAGE}/${TF_A_NAME}-bl31.bin
}
addtask deploy before do_deploy_deb after do_dpkg_build