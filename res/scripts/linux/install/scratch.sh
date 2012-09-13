GIT_DEFAULT="/usr/bin/git"
ANT_DEFAULT="/usr/bin/ant"


check_cmd() {
	CMD_LOCATION=$2
	CMD_NAME=$1
	echo -n "Checking if ${CMD_NAME} is installed... "
	if [ ! -x $CMD_LOCATION ];
        then
		echo
                echo $CMD_NAME seems not to be installed.
                echo Please specify $CMD_NAME location.
                echo If you do not have $CMD_NAME installed, please cancel
                echo with ^C and install $CMD_NAME.
                echo
                echo -n "${CMD_NAME} Location [${CMD_LOCATION}]: " 
                read CMD_LOCATION
		check_cmd "$CMD_NAME" "$CMD_LOCATION"
        fi
	export cmd="$CMD_LOCATION"
	echo $CMD_LOCATION 
}

sys_check() {
        # check if git is installed
	echo
	echo "####################################################"
        echo "# CHECKING SYSTEM REQUIREMENTS                     #"
        echo "####################################################"
	echo
        check_cmd "git" $GIT_DEFAULT
	git=$cmd
	check_cmd "ant" $ANT_DEFAULT
	ant=$cmd

}

scratch() {
	echo Installing COSI from scratch.
	echo
	echo Do you want to install CosiX aswell?
	echo This will require a graphical user interface
	echo and is not needed on servers.
	echo
	echo "Type 'yes' to install CosiX afterwards."
	read COSIX_INSTALL
	
	sys_check

	. install/cosi.sh
	cosi_install

	echo
	echo
	echo
	echo Thank you for using COSI. 
	echo If you want to contribute to this project or
	echo read more about the COSI philosophie, please
	echo visit http://the.cosi-project.org

	# this must be the last statement 
	if [ "$COSIX_INSTALL" == "yes" ];
	then
		echo
		echo -n "Do you want to continue installing CosiX? (Y/n) "
		read COSIX_CONTINUE
		if [ "$COSIX_CONTINUE" == "n" ];
		then
			echo Aborting.
			exit
		fi
		echo Starting CosiX install.
		. install/cosix.sh
		cosix_install
	fi
}
