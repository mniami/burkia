using Contracts.Models;
using System;
using System.Collections.Generic;
using System.Data.Entity;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Contracts
{
    public class DataService
    {
        public List<Offer> GetOffers(DbContext dbConext)
        {
            var list = dbConext.Set<Offer>().Where(p=>!p.offerId.HasValue).ToList();
            return list;       
        }
    }
}
