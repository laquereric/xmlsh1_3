# test json:array
import commands json=json
echo json:array()
echo json:array( 1  json:number(2.5)  \
    json:object( x hi y json:array( json:null() json:boolean(true)  "text" ) ) )
