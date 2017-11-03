using CSC3045_CS2.Exception;
using CSC3045_CS2.Models;
using CSC3045_CS2.Service;
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
    /// Interaction logic for CreateProject.xaml
    /// </summary>
    public partial class CreateProject : Page
    {

        private ProjectClient _client;

        #region Public Variables
        User currentUser = (User)Application.Current.Properties["user"];

        public String TitleLabel { get; set; } = "Create A Project";

        public String ProjectManagerLabel { get; set; } = "Project Manager:";

        public String ProjectNameLabel { get; set; } = "Project Name:";

        public String DescriptionLabel { get; set; } = "Description:";

        public String ProductOwnerLabel { get; set; } = "Product Owner";

        public String ProjectManagerNameLabel { get; set; } = "";

        public String ProjectNameTextContent { get; set; } = "";

        public String DescriptionTextContent { get; set; } = "";

        public String CreateButtonText { get; set; } = "Create";

        #endregion
        public CreateProject()
        {
            InitializeComponent();
            DataContext = this;
            _client = new ProjectClient();
            pageSetup();
        }

        #region Command methods

        public void pageSetup()
        {     
            ProjectManagerNameLabel = currentUser.Forename + " " + currentUser.Surname;
        }

        public ICommand CreateProjectCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    Project project = new Project(currentUser, ProjectNameTextContent, DescriptionTextContent);
                    project.Manager = (User)Application.Current.Properties["user"];

                    try
                    {
                        _client.CreateProject(project);

                        MessageBox.Show("Project creation successful!");

                        Page userDashboard = new UserDashboard();

                        NavigationService.GetNavigationService(this).Navigate(userDashboard);
                    }
                    catch (RestResponseErrorException ex)
                    {
                        MessageBox.Show(ex.Message);
                    }
                });
            }
        }
        #endregion
    }
}
