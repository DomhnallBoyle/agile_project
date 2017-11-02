using CSC3045_CS2.Exception;
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
    /// Interaction logic for UserDashboard.xaml
    /// </summary>
    public partial class UserDashboard : Page
    {
        #region Private variables

        private List<Project> _projectList;
        private TextBlock _projectName, _projectDescription;
        private Button _toProjectDashboard;

        #endregion

        #region Public variables

        public String UserLabel { get; set; } = ((User)Application.Current.Properties["user"]).GetFullName();

        #endregion

        public UserDashboard()
        {
            InitializeComponent();
            DataContext = this;
            _projectList = setTestData();
            addProjectsToUIList();
        }

        private void addProjectsToUIList()
        {
            for (int i=0; i<_projectList.Count; i++)
            {
                _projectName = new TextBlock
                {
                    Text = _projectList[i].Name
                };
                _projectDescription = new TextBlock
                {
                    Text = _projectList[i].Description
                };
                _toProjectDashboard = new Button
                {
                    Content = "Go to Project",
                    Command = GoToCommand,
                    CommandParameter = i
                };

                ProjectNamePanel.Children.Add(_projectName);
                DetailsPanel.Children.Add(_projectDescription);
                ButtonPanel.Children.Add(_toProjectDashboard);
            }
        }

        #region Class methods

        private List<Project> setTestData()
        {
            User user1 = new User("Ji", "Ming", "ji.ming@gmail.com", null);
            User user2 = new User("John", "Bustard", "jbustard@gmail.com", null);
            User user3 = new User("Pat", "Mustard", "milkman@gmail.com", null);
            User user4 = new User("Ted", "Crilly", "itwasonlyrestinginmyaccount@gmail.com", null);
            User user5 = new User("Todd", "Umptious", "goldencleric@gmail.com", null);

            Project projectOne = new Project(user1, "Calypso", "Android App", user2);
            Project projectTwo = new Project(user2, "Cornetto", "Java app", user3);
            Project projectThree = new Project(user5, "Twister", "c# app", user4);
            projectOne.Id = 1;
            return new List<Project> { projectOne, projectTwo, projectThree };
        }

        #endregion

        #region Command methods

        public ICommand GoToCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    try
                    {
                        var projectNumber = ((int)param);
                        Page projectDashboard = new ProjectDashboard(_projectList, projectNumber);

                        NavigationService.GetNavigationService(this).Navigate(projectDashboard);
                    }
                    catch (RestResponseErrorException ex)
                    {
                        MessageBox.Show(ex.Message);
                    }
                });
            }
        }


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
