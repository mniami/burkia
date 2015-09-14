using System.ComponentModel.DataAnnotations.Schema;
using System.Data.Entity.ModelConfiguration;

namespace Contracts.Models.Mapping
{
    public class OfferMap : EntityTypeConfiguration<Offer>
    {
        public OfferMap()
        {
            // Primary Key
            this.HasKey(t => t.Id);

            // Properties
            this.Property(t => t.name)
                .IsFixedLength()
                .HasMaxLength(64);

            this.Property(t => t.description)
                .IsFixedLength()
                .HasMaxLength(128);

            this.Property(t => t.descriptonLong)
                .IsFixedLength()
                .HasMaxLength(1024);

            this.Property(t => t.imageUrl)
                .IsFixedLength()
                .HasMaxLength(256);

            this.Property(t => t.imageUrl2)
                .IsFixedLength()
                .HasMaxLength(256);

            this.Property(t => t.imageUrl3)
                .IsFixedLength()
                .HasMaxLength(256);

            this.Property(t => t.imageUrl4)
                .IsFixedLength()
                .HasMaxLength(256);

            this.Property(t => t.imageUrl5)
                .IsFixedLength()
                .HasMaxLength(256);

            // Table & Column Mappings
            this.ToTable("Offer");
            this.Property(t => t.Id).HasColumnName("Id");
            this.Property(t => t.name).HasColumnName("name");
            this.Property(t => t.description).HasColumnName("description");
            this.Property(t => t.descriptonLong).HasColumnName("descriptonLong");
            this.Property(t => t.imageUrl).HasColumnName("imageUrl");
            this.Property(t => t.imageUrl2).HasColumnName("imageUrl2");
            this.Property(t => t.imageUrl3).HasColumnName("imageUrl3");
            this.Property(t => t.imageUrl4).HasColumnName("imageUrl4");
            this.Property(t => t.imageUrl5).HasColumnName("imageUrl5");
            this.Property(t => t.offerId).HasColumnName("offerId");
            this.Property(t => t.type).HasColumnName("type");

            // Relationships
            this.HasOptional(t => t.Offer2)
                .WithMany(t => t.Offer1)
                .HasForeignKey(d => d.offerId);

        }
    }
}
