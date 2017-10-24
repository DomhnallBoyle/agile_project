using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace CSC3045_CS2.Pages
{
    /// <summary>
    /// Interaction logic for Login.xaml
    /// </summary>
    public partial class Login : Page
    {
        #region Public Variables

        public String LoginTitle { get; set; } = "Login";

        public String LoginUsernameLabel { get; set; } = "Username: ";

        public String LoginPasswordLabel { get; set; } = "Password: ";

        #endregion

        public Login()
        {
            InitializeComponent();

            DataContext = this;
        }
    }
}
