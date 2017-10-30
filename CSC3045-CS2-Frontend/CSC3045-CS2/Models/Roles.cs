using RestSharp.Deserializers;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace CSC3045_CS2.Models
{
    /// <summary>
    /// Model for the User Role which is selected on Register.
    /// </summary>
    public class Roles
    {
        #region public getters & setters

        [DeserializeAs(Name = "id")]
        public long Id { get; set; }

        [DeserializeAs(Name = "productOwner")]
        public bool ProductOwner { get; set; }

        [DeserializeAs(Name = "scrumMaster")]
        public bool ScrumMaster { get; set; }

        [DeserializeAs(Name = "developer")]
        public bool Developer { get; set; }

        #endregion

        public Roles() { }

        /// <summary>
        /// Constructor for Role model
        /// Builds upon the notion that a user can have multiple roles
        /// All 3 booleans from checkboxes passed into the constructor
        /// </summary>
        /// <param name="productOwner"></param>
        /// <param name="scrumMaster"></param>
        /// <param name="developer"></param>
        public Roles(bool productOwner, bool scrumMaster, bool developer)
        {
            this.ProductOwner = productOwner;
            this.ScrumMaster = scrumMaster;
            this.Developer = developer;
        }
    }
}
