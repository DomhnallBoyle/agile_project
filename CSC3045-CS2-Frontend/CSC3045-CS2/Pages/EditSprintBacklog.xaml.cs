using CSC3045_CS2.Exception;
using CSC3045_CS2.Models;
using CSC3045_CS2.Service;
using CSC3045_CS2.Utility;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
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
    /// Interaction logic for EditSprintBacklog.xaml
    /// </summary>
    public partial class EditSprintBacklog : BasePage
    {
        #region Private Variables
        private SprintClient _sprintClient;
        private UserStoryClient _userStoryClient;
        #endregion

        #region Public Variables
        public Sprint CurrentSprint { get; set; }
        public ObservableCollection<UserStory> AvailableStories { get; set; }
        public ObservableCollection<UserStory> SprintBacklog { get; set; }
        #endregion

        public EditSprintBacklog(Sprint sprint)
        {
            InitializeComponent();
            CurrentPage = this.Title;
            DataContext = this;

            _sprintClient = new SprintClient();
            _userStoryClient = new UserStoryClient();

            CurrentSprint = sprint;

            AvailableStories = new ObservableCollection<UserStory>(_userStoryClient.GetAvailableUserStories(sprint.Project.Id));
            SprintBacklog = new ObservableCollection<UserStory>(_sprintClient.GetSprintBacklog(sprint.Project.Id, sprint.Id));
        }

        #region Command methods

        public ICommand SaveBacklogCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    try
                    {
                        List<UserStory> sprintBacklog = SprintBacklog.ToList<UserStory>();
                        CurrentSprint.UserStories = sprintBacklog;

                        _sprintClient.UpdateSprintBacklog(CurrentSprint);

                        MessageBoxUtil.ShowSuccessBox("Backlog saved");

                        Page sprintDashboardPage = new SprintDashboard(CurrentSprint, false);

                        NavigationService.GetNavigationService(this).Navigate(sprintDashboardPage);
                    }
                    catch (RestResponseErrorException ex)
                    {
                        MessageBoxUtil.ShowErrorBox(ex.Message);
                    }
                });
            }
        }


        public ICommand BackCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    try
                    {
                        Page sprintDashboardPage = new SprintDashboard(CurrentSprint, false);

                        NavigationService.GetNavigationService(this).Navigate(sprintDashboardPage);
                    }
                    catch (RestResponseErrorException ex)
                    {
                        MessageBoxUtil.ShowErrorBox(ex.Message);
                    }
                });
            }
        }

        #endregion
    }
}

