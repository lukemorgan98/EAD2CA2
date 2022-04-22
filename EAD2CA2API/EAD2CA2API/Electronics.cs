using System.ComponentModel.DataAnnotations;

namespace EAD2CA2API
{
    public class Electronics
    {
        public int ID { get; set; }

        public string Name { get; set; }

        public double Price { get; set; }

        public string brandName { get; set; }

        public string size { get; set; }

        public string colour { get; set; }

        public int SellerID { get; set; }
    }
}
