#
# Copyright (c) Siemens AG, 2026
#
# SPDX-License-Identifier: MIT

inherit linux-kernel

ARCHIVE_VERSION = "${@ d.getVar('PV')[:-2] if d.getVar('PV').endswith('.0') else d.getVar('PV') }"

SRC_URI += "https://cdn.kernel.org/pub/linux/kernel/v6.x/linux-${ARCHIVE_VERSION}.tar.xz"
SRC_URI[sha256sum] = "e35ac999f40a6874493d8d60f33f1150d7a89ae5841c428da82257fbcd070aed"

S = "${WORKDIR}/linux-${ARCHIVE_VERSION}"

KERNEL_DEFCONFIG = "defconfig"

LINUX_VERSION_EXTENSION = "-isar"

COMPATIBLE_MACHINE = "^(imx8mmlpd4-evk)$"