using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Contracts
{
    public class DataMap
    {
        public restaurantmenu.remotecontracts.Offer Convert(Contracts.Models.Offer offer)
        {
            return ConvertSimple(offer);
        }
        private restaurantmenu.remotecontracts.Offer ConvertSimple(Contracts.Models.Offer offer)
        {
            return new restaurantmenu.remotecontracts.Offer
            {
                Description = offer.description,
                ImageUrl = offer.imageUrl,
                Name = offer.name,
                Type = offer.type,
                Offers = offer.Offer1.Select(p=>ConvertSimple(p)).ToList()
            };
        }
    }
}
