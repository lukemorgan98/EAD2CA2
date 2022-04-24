using System;
using Microsoft.EntityFrameworkCore;
using System.Collections.Generic;
using System.Linq;


namespace EAD2CA2_EFCORE
{
   

        public class Seller
        {
            public int ID { get; set; }                 // PK and identity
            public string Name { get; set; }            // null
            public string Location { get; set; }
            public int Rating { get; set; }
            public int ElectronicsSold { get; set; }
            // navigation property to electronics that the seller sells, virtual => lazy loading  
            public virtual ICollection<Electronic> Electronics { get; set; }
        }

        public class Electronic
        {
            public int ID { get; set; }                             // PK and identity
            public string Name { get; set; }                        // null
            public double Price { get; set; }                        // not null, use int? for null
            public string BrandName { get; set; }
            public string Size { get; set; }
            public string Colour { get; set; }
            // foreign key property, null, follows convention for naming
            public int? SellerID { get; set; }
            // update relationship through this property, not through navigation property
                            

            // navigation property to Seller for this module
            public virtual Seller Seller { get; set; }           // virtual enables "lazy loading" 
        }

        // context class
        public class ElectronicContext : DbContext
        {
            // localDB connection string
            // c:\users\Aaron\electronicDB1.mdf
            protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
            {
                optionsBuilder.UseSqlServer(@"Server=tcp:ead2ca2sn.database.windows.net,1433;Initial Catalog=EAD2_CA2_DB;Persist Security Info=False;User ID=ead_admin;Password=Semester8;MultipleActiveResultSets=False;Encrypt=True;TrustServerCertificate=False;Connection Timeout=30;") ;
            }

            public DbSet<Seller> Sellers { get; set; }
            public DbSet<Electronic> Electronics { get; set; }
        }


        class ElectronicRepository
        {

            // print sellers, their ids, names, and the names of electronics they sell 
            public void DoSellerQuery()
            {
                using ElectronicContext db = new ElectronicContext();

                var sellers = db.Sellers.OrderBy(s => s.ID).Select(s => new { Id = s.ID, Name = s.Name, Location = s.Location, Rating = s.Rating, ElectronicsSold = s.ElectronicsSold, Electronic= s.Electronics });
                Console.WriteLine(sellers.ToQueryString());

                Console.WriteLine("\nSeller:");
                foreach (var seller in sellers)
                {
                    Console.WriteLine("id: " + seller.Id + " name: " + seller.Name);
                    Console.WriteLine("sells: ");

                    // Modules is a navigation propery of type ICollection<Module>
                    var SellerElectronics = seller.Electronic;
                    foreach (var sellerElectronic in SellerElectronics)
                    {
                        Console.WriteLine(sellerElectronic.Name);
                    }
                }
            }

            // prints the ID and name of each electronic thats available and the name of the seller who sells it
            public void DoElectronicQuery()
            {
                using ElectronicContext db = new ElectronicContext();

                // select all modules, ordered by module name
                var electronics = db.Electronics.OrderBy(electronic=> electronic.ID);       // load

                Console.WriteLine("\nElectronics:");
                foreach (var electronic in electronics)
                {
                    Console.WriteLine("id: " + electronic.ID + " name: " + electronic.Name + " ");

                    if (electronic.Seller != null)
                    {
                        // Seller is a navigation property of type Seller
                        Console.WriteLine(" solded by: " + electronic.Seller.Name);
                    }
                }
            }

            // add a seller
            public void AddSeller(Seller seller)
            {
                using ElectronicContext db = new ElectronicContext();

                try
                {
                    // add and save
                    db.Sellers.Add(seller);
                    db.SaveChanges();

                }
                catch (Exception e)
                {
                    Console.WriteLine(e.ToString());
                }
            }

            // add electronics , contains SellerID
            public void AddElectronic(Electronic electronic)
            {
                using ElectronicContext db = new ElectronicContext();

                try
                {
                    // add and save
                    db.Electronics.Add(electronic);
                    db.SaveChanges();
                    // navigation properties updated on both sides
                }
                catch (Exception e)
                {
                    Console.WriteLine(e.ToString());
                }

            }


            static class EFCore
            {
                static void Main()
                {

                    ElectronicRepository repository = new ElectronicRepository();
                
                    Seller currys = new Seller() { Name = "Currys", Location = "Naas", Rating = 3, ElectronicsSold = 100 };
                    repository.AddSeller(currys);         // ID now assigned

                    // sells 3 Electronic items
                    Electronic phone = new Electronic() { Name = "Iphone 11", Price = 629, BrandName = "Apple", Size = "64 GB" , Colour = "red", SellerID = currys.ID };
                    Electronic laptop = new Electronic() { Name = "Surface Laptop 15 inches", Price = 1500, BrandName = "Microsoft", Size = "256 GB", Colour = "silver", SellerID = currys.ID };

                    Electronic tv = new Electronic() { Name = "BRAVIA 4K", Price = 80, BrandName = "Sony", Size = "48 Inches", Colour = "Black", SellerID=currys.ID };   

                    repository.AddElectronic(phone);
                    repository.AddElectronic(laptop);
                    repository.AddElectronic(tv);

                    repository.DoSellerQuery();
                    repository.DoElectronicQuery();

                    Console.ReadLine();
                }
            }
        }
    }


