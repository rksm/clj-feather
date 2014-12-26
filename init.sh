if [[ -z $CLOJURE_FEATHER ]]; then
    echo "Error! CLOJURE_FEATHER environment variable not set"
    exit 1
fi

local_pom=clojure-feather-pom.xml

if [[ ! -f $local_pom ]]; then
    ln -sf $CLOJURE_FEATHER/pom.xml clojure-feather-pom.xml
fi
