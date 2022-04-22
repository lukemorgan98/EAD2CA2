using System.ComponentModel.DataAnnotations;

namespace EAD2CA2API
{
    public class Sellers
    {
        [Required]
        [Key]
        public int ID { get; set; }

        [Required]
        public string Name { get; set; }

        public string Location { get; set; }

        public int Rating { get; set; }

        public int ElectronicsSold { get; set; }
    }
}
