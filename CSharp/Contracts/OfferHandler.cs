
using Contracts.Models;
using restaurantmenu.remotecontracts;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Web;
using Thrift.Server;
using Thrift.Transport;


namespace Contracts
{
    public class OfferHandler : OfferService.Iface
    {
        private DataService service = new DataService();
        private DataMap map = new DataMap();

        public OfferHandler()
        {
        }
        public List<restaurantmenu.remotecontracts.Offer> getOffers()
        {
            try {
                using (var context = new guidemeContext())
                {
                    var offers = service.GetOffers(context).Select(p => map.Convert(p)).ToList();
                    return offers;
                }
            }
            catch (Exception ex)
            {
                return new List<restaurantmenu.remotecontracts.Offer>();
            }
        }
    }
}
