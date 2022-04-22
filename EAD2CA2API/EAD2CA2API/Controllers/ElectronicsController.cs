using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using EAD2CA2API;
using EAD2CA2API.Data;

namespace EAD2CA2API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class ElectronicsController : ControllerBase
    {
        private readonly EAD2CA2APIContext _context;

        public ElectronicsController(EAD2CA2APIContext context)
        {
            _context = context;
        }

        // GET: api/Electronics
        [HttpGet]
        public async Task<ActionResult<IEnumerable<Electronics>>> GetElectronics()
        {
            return await _context.Electronics.ToListAsync();
        }

        // GET: api/Electronics/5
        [HttpGet("{id}")]
        public async Task<ActionResult<Electronics>> GetElectronics(int id)
        {
            var electronics = await _context.Electronics.FindAsync(id);

            if (electronics == null)
            {
                return NotFound();
            }

            return electronics;
        }

        // PUT: api/Electronics/5
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPut("{id}")]
        public async Task<IActionResult> PutElectronics(int id, Electronics electronics)
        {
            if (id != electronics.ID)
            {
                return BadRequest();
            }

            _context.Entry(electronics).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!ElectronicsExists(id))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return NoContent();
        }

        // POST: api/Electronics
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost]
        public async Task<ActionResult<Electronics>> PostElectronics(Electronics electronics)
        {
            _context.Electronics.Add(electronics);
            await _context.SaveChangesAsync();

            return CreatedAtAction("GetElectronics", new { id = electronics.ID }, electronics);
        }

        // DELETE: api/Electronics/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteElectronics(int id)
        {
            var electronics = await _context.Electronics.FindAsync(id);
            if (electronics == null)
            {
                return NotFound();
            }

            _context.Electronics.Remove(electronics);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        private bool ElectronicsExists(int id)
        {
            return _context.Electronics.Any(e => e.ID == id);
        }
    }
}
