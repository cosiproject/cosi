#!/bin/bash
#
# Cosi install script
#
##########################
. install/include.sh

case $1 in
	scratch)
		scratch
	;;
	help)
		ci_help
	;;
	*)
		ci_help
	;;
esac
