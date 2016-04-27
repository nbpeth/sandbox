import groovyx.gpars.GParsPool
import wslite.rest.RESTClient

public class UriZapper {
    def uri
    def volume

    public UriZapper(volume, uri){
        this.volume = Integer.parseInt(volume)
        this.uri = uri
    }
    
    public static void main(String[] args) {
        if (args.length <= 0) throw new Exception("where your args at")

        UriZapper zapper = new UriZapper(args[0], args[1])
        zapper.doIt()

    }
    
    public void doIt(){
        def list = []
        for(int i=0; i < volume; i++){
            list.add(uri)
        }

        int a = 0
        GParsPool.withPool(50, {
            list.eachParallel { url ->
                RESTClient client = new RESTClient(url)
                def response = client.get()
                println ("${response.statusCode} :::::")
                println response.getContentAsString()
                if(a++ % 50 == 0) println a
            }
        })
    }



}
