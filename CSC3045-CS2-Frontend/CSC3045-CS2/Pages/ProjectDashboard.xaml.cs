using CSC3045_CS2.Models;
using CSC3045_CS2.Utility;
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
    /// Interaction logic for ProjectDashboard.xaml
    /// </summary>
    public partial class ProjectDashboard : Page
    {
        #region Public Variables

        public String WelcomeProductManagerLabel { get; set; } = "Welcome!";

        public String ProductManagerLabel { get; set; } = "";

        public String CreateProjectButtonLabel { get; set; } = "Create Project";

        User currentUser = (User)Application.Current.Properties["user"];
        #endregion




        public ProjectDashboard()
        {
            InitializeComponent();
            DataContext = this;
            pageSetup();
        }
        public void pageSetup()
        {
            ProductManagerLabel = currentUser.Forename + " " + currentUser.Surname;
        }
        #region Command methods
        public ICommand NavigateToCreateProjectCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    Page createProjectPage = new CreateProject();

                    NavigationService.GetNavigationService(this).Navigate(createProjectPage);
                });
            }
        }
        #endregion
    }
}
