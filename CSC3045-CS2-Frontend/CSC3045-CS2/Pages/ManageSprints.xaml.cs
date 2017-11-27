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
    /// Interaction logic for ManageSprints.xaml
    /// </summary>
    public partial class ManageSprints : BasePage
    {
        # region Private variables

        private SprintClient _client;

        #endregion

        #region Public variables

        public Project CurrentProject { get; set; }
        public List<Sprint> Sprints { get; set; }
        public Permissions Permissions { get; set; }


        #endregion

        public ManageSprints(Project project)
        {
            InitializeComponent();
            CurrentPage = this.Title;
            DataContext = this;
            
            _client = new SprintClient();

            Permissions = new Permissions((User)Application.Current.Properties["user"], project);

            CurrentProject = project;

            try
            {
                Sprints = _client.GetSprintsInProject(CurrentProject.Id);
            }
            catch (RestResponseErrorException ex)
            {
                if (ex.StatusCode == System.Net.HttpStatusCode.NotFound)
                {
                    MessageBoxUtil.ShowInfoBox(ex.Message);
                }
                else
                {
                    MessageBoxUtil.ShowErrorBox(ex.Message);
                }
            }
        }

        #region Command and Event methods
       

        public ICommand NavigateToCreateSprintCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    Page createSprintPage = new CreateSprint(CurrentProject);

                    NavigationService.GetNavigationService(this).Navigate(createSprintPage);
                });
            }
        }

        public ICommand BackCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    Page projectDashboardPage = new ProjectDashboard(CurrentProject);

                    NavigationService.GetNavigationService(this).Navigate(projectDashboardPage);
                });
            }
        }

        private void SprintListBox_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            Sprint selectedSprint = (Sprint)SprintListBox.SelectedItem;
            selectedSprint.Project = CurrentProject;

            Page sprintDashboardPage = new SprintDashboard(selectedSprint, false);

            NavigationService.GetNavigationService(this).Navigate(sprintDashboardPage);
        }

        #endregion
    }
}
