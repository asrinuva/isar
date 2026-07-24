#
# Copyright (c) Siemens AG, 2026
#
# SPDX-License-Identifier: MIT

inherit u-boot

MAINTAINER = "isar-users <isar-users@googlegroups.com>"

COMPATIBLE_MACHINE = "^(imx8mmlpd4-evk)$"

SRC_URI += "git://github.com/nxp-imx/uboot-imx.git;protocol=https;branch=lf_v2026.04"
SRCREV = "6eeef838dac4ddbc06ff14450531a95e8c5cb346"

S = "${WORKDIR}/git"

U_BOOT_CONFIG ?= "imx8mm_evk_defconfig"
U_BOOT_BIN ?= "flash.bin"
U_BOOT_BIN_INSTALL = "flash.bin u-boot.bin u-boot-nodtb.bin u-boot.dtb spl/u-boot-spl.bin"
U_BOOT_EXTRA_BUILDARGS = "BL31=${S}/bl31.bin"

DEPENDS += "trusted-firmware-a-imx8mmlpd4-evk"
do_prepare_build[depends] += "trusted-firmware-a-imx8mmlpd4-evk:do_deploy_deb"

DEBIAN_BUILD_DEPENDS .= ", \
    libssl-dev:native, \
    libssl-dev:${DISTRO_ARCH}, \
    python3-dev:native, \
    python3-setuptools, \
    swig, \
    trusted-firmware-a-imx8mmlpd4-evk"

do_prepare_build:append() {
    bl31_deb=$(find ${REPO_ISAR_DIR}/${DISTRO} -name 'trusted-firmware-a-imx8mmlpd4-evk_*_${DISTRO_ARCH}.deb' -print -quit)
    [ -n "${bl31_deb}" ] || bbfatal "trusted-firmware-a-imx8mmlpd4-evk .deb not found in ${REPO_ISAR_DIR}/${DISTRO}"
    dpkg --fsys-tarfile ${bl31_deb} | \
        tar xOf - ./usr/lib/trusted-firmware-a/imx8mmlpd4-evk/bl31.bin \
        > ${S}/bl31.bin
}