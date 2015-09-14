using restaurantmenu.remotecontracts;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Thrift.Server;
using Thrift.Transport;

namespace Contracts
{
    public class OfferServer
    {
        public void Serv()
        {
            Task.Run(() => {
                OfferService.Processor processor = new OfferService.Processor(new OfferHandler());
                TServerTransport serverTransport = new TServerSocket(9095);
                TServer server = new TSimpleServer(processor, serverTransport);
                Console.WriteLine("Starting the server...");
                server.Serve();
            });
        }
    }
}
