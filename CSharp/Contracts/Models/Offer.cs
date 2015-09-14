using System;
using System.Collections.Generic;

namespace Contracts.Models
{
    public partial class Offer
    {
        public Offer()
        {
            this.Offer1 = new List<Offer>();
        }

        public int Id { get; set; }
        public string name { get; set; }
        public string description { get; set; }
        public string descriptonLong { get; set; }
        public string imageUrl { get; set; }
        public string imageUrl2 { get; set; }
        public string imageUrl3 { get; set; }
        public string imageUrl4 { get; set; }
        public string imageUrl5 { get; set; }
        public Nullable<int> offerId { get; set; }
        public virtual ICollection<Offer> Offer1 { get; set; }
        public virtual Offer Offer2 { get; set; }
        public int type { get; set; }
    }
}
