using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using EAD2CA2API;

namespace EAD2CA2API.Data
{
    public class EAD2CA2APIContext : DbContext
    {
        public EAD2CA2APIContext (DbContextOptions<EAD2CA2APIContext> options)
            : base(options)
        {
        }

        public DbSet<EAD2CA2API.Electronics> Electronics { get; set; }

        public DbSet<EAD2CA2API.Sellers> Sellers { get; set; }
    }
}
