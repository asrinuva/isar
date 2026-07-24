#
# Copyright (c) Siemens AG, 2026
#
# SPDX-License-Identifier: MIT

inherit u-boot

MAINTAINER = "isar-users <isar-users@googlegroups.com>"

COMPATIBLE_MACHINE = "^(imx8mmlpd4-evk)$"

SRC_URI += "git://github.com/nxp-imx/uboot-imx.git;protocol=https;branch=lf_v2026.04 \
    file://rules-imx8mmlpd4-evk.tmpl \
    file://imx8mmlpd4-evk.its"
SRCREV = "6eeef838dac4ddbc06ff14450531a95e8c5cb346"

S = "${WORKDIR}/git"

U_BOOT_CONFIG ?= "imx8mm_evk_defconfig"
U_BOOT_BIN ?= "flash.bin"
U_BOOT_BIN_INSTALL = "flash.bin u-boot.bin u-boot-nodtb.bin u-boot.dtb spl/u-boot-spl.bin"
U_BOOT_EXTRA_BUILDARGS = "BL31=${S}/bl31.bin"
TEMPLATE_FILES += "rules-imx8mmlpd4-evk.tmpl"

DEPENDS += "trusted-firmware-a-imx8mmlpd4-evk"
do_prepare_build[depends] += "trusted-firmware-a-imx8mmlpd4-evk:do_deploy_deb"

DEBIAN_BUILD_DEPENDS .= ", \
    efitools:native, \
    libgnutls28-dev:native, \
    libgnutls28-dev:${DISTRO_ARCH}, \
    libssl-dev:native, \
    libssl-dev:${DISTRO_ARCH}, \
    python3-dev:native, \
    python3-setuptools, \
    swig, \
    trusted-firmware-a-imx8mmlpd4-evk"

do_prepare_build:append() {
    cp ${DEPLOY_DIR_IMAGE}/imx8mmlpd4-evk-bl31.bin ${S}/bl31.bin
    cp ${WORKDIR}/rules-imx8mmlpd4-evk ${S}/debian/rules
    cp ${WORKDIR}/imx8mmlpd4-evk.its ${S}/u-boot.its
}