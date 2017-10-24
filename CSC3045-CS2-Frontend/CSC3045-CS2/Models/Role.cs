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
    public class Role
    {
        #region public getters & setters

        public bool IsProductOwner { get; set; }
        public bool IsScrumMaster { get; set; }
        public bool IsDeveloper { get; set; }

        #endregion

        /// <summary>
        /// Constructor for Role model
        /// Builds upon the notion that a user can have multiple roles
        /// All 3 booleans from checkboxes passed into the constructor
        /// </summary>
        /// <param name="isProductOwner"></param>
        /// <param name="isScrumMaster"></param>
        /// <param name="isDeveloper"></param>
        public Role(bool isProductOwner, bool isScrumMaster, bool isDeveloper)
        {
            this.IsProductOwner = isProductOwner;
            this.IsScrumMaster = isScrumMaster;
            this.IsDeveloper = isDeveloper;
        }
    }
}
