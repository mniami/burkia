using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using Contracts.Models.Mapping;

namespace Contracts.Models
{
    public partial class guidemeContext : DbContext
    {
        static guidemeContext()
        {
            Database.SetInitializer<guidemeContext>(null);
        }

        public guidemeContext()
            : base("Name=guidemeContext")
        {
        }

        public DbSet<Offer> Offers { get; set; }

        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            modelBuilder.Configurations.Add(new OfferMap());
        }
    }
}
