using Contracts;
using Contracts.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace WebApplication1.Controllers
{
    public class MenuController : Controller
    {
        public ActionResult Main()
        {
            return View();
        }

        public ActionResult About()
        {
            ViewBag.Message = "Guide me team ;)";

            DataService dataService = new DataService();
            using (var dbContext = new guidemeContext())
            {
                dataService.GetOffers(dbContext);
            }

            return View();
        }

        public ActionResult Contact()
        {
            ViewBag.Message = "Contact us";

            return View();
        }
        public ActionResult Underdevelopment()
        {
            return View();
        }
    }
}