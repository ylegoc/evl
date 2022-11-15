#!/usr/bin/bash

echo "Deploy EVL site"

if [ $# -lt 1 ]
then
	echo "Argument: <evl path>"
	exit 1
fi

evlpath=$1

echo "Generate web site"
hugo

echo "Remove docs in $evlpath"
rm -fr "$evlpath/docs"

echo "Copy to $evlpath"
mv "public" "$evlpath/docs"

echo "Copy Javadoc"
cp -r "$evlpath/evl-lib/target/apidocs" "$evlpath/docs"

echo "Done"
