COSI_REPO="https://github.com/cosiproject/cosi"
COSITK_REPO="https://github.com/cosiproject/cositk"


cosi_clean() {
	rm -rf tmp/
}
cosi_install() {
	cosi_clean

	mkdir tmp/
	cd tmp/
	git clone $COSI_REPO
	cd cosi
	ant
}
