for i in `find . -name pom.xml`; do echo $i; sed "s#7.1.0.Beta1-redhat-1#7.1.0.Beta1-redhat-2#g" -i $i; sed "s#7.1.0.DR16#7.1.0.DR17#g" -i $i; done
