using CSC3045_CS2.Exception;
using CSC3045_CS2.Models;
using CSC3045_CS2.Pages;
using CSC3045_CS2.Service;
using CSC3045_CS2.Utility;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;
using System.Windows.Navigation;

namespace CSC3045_CS2
{
    /// <summary>
    /// Interaction logic for ProductBacklog.xaml
    /// </summary>
    public partial class ProductBacklog : BasePage
    {
        #region Private Variables

        private UserStoryClient _client;

        private ObservableCollection<UserStory> _backlog = new ObservableCollection<UserStory>();

        #endregion

        #region Public Variables

        public Project CurrentProject { get; set; }

        public Permissions Permissions { get; set; }

        public PermissonDragHandler PermissonDragHandler { get; set; }

        public ObservableCollection<UserStory> Backlog { get; set; }

        public string PageLabel { get; set; } = "Product Backlog";

        public string MarketValueLabel { get; set; } = "Market Value";

        public string BackButtonLabel { get; set; } = "Back";

        public string CreateStoryButtonLabel { get; set; } = "Create Story";

        public string UpdateStory { get; set; } = "Update";

        public ObservableCollection<int> StoryPoints { get; set; } = new ObservableCollection<int>() { 0, 1, 2, 3, 5, 8, 13, 20, 40, 100 };

        #endregion

        public ProductBacklog(Project project)
        {
            InitializeComponent();
            CurrentPage = this.Title;
            DataContext = this;
            _client = new UserStoryClient();

            CurrentProject = project;

            Permissions = new Permissions((User)Application.Current.Properties["user"], project);
            PermissonDragHandler = new PermissonDragHandler(Permissions.ProductOwner);

            try
            {
                var sortedOC = from item in _client.GetUserStories(project.Id) orderby item.Index select item;
                Backlog = new ObservableCollection<UserStory>(sortedOC.ToList());
            }
            catch (RestResponseErrorException ex)
            {
                MessageBoxUtil.ShowErrorBox(ex.Message);
            }
        }

        /// <summary>
        /// Performs Regex Validation live on the TextBox's as data is entered
        /// Ensures that only digits can be entered into the TextBox
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void NumberOnlyTextBoxValidation(object sender, TextCompositionEventArgs e)
        {
            Utility.Validation.NumberOnlyTextBoxValidation(e);
        }

        #region ICommands

        /// <summary>
        /// Returns to the project dashboard
        /// </summary>
        /// 
        public ICommand BackCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    Page projectDashboard = new ProjectDashboard(CurrentProject);

                     NavigationService.GetNavigationService(this).Navigate(projectDashboard);

                });
            }
        }
       
        /// <summary>
        /// Changes order of user stories before
        /// sending the updated list to the backend
        /// </summary>
        public ICommand SaveOrder
        {
            get
            {
                return new RelayCommand(param =>
                {
                    for (int i = 0; i < Backlog.Count; i++)
                    {
                        Backlog[i].Index = i + 1;
                    }

                    try
                    {
                        _client.SaveOrder(Backlog.ToList());
                        MessageBoxUtil.ShowSuccessBox("User stories updated successfully");
                    }
                    catch (RestResponseErrorException ex)
                    {
                        MessageBoxUtil.ShowErrorBox(ex.Message);
                    }
                });
            }
        }

        /// <summary>
        /// Navigates to the Story creation page
        /// </summary>
        public ICommand CreateStoryCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    Page createUserStoryPage = new CreateUserStory(CurrentProject);

                    NavigationService.GetNavigationService(this).Navigate(createUserStoryPage);
                });
            }
        }

        /// <summary>
        /// Mouse up event on user story backlog
        /// Clicking on user story will take the user to the Acceptance test page
        /// </summary>
        /// <param name="sender">ListBoxItem that is clicked</param>
        /// <param name="e"></param>
        private void PreviewMouseUp(object sender, MouseButtonEventArgs e)
        {
            UserStory selectedStory = (UserStory)(sender as ListBoxItem).DataContext;
            Page userStoryDetailsPage = new UserStoryDetails(selectedStory);
            NavigationService.GetNavigationService(this).Navigate(userStoryDetailsPage);
        }

        /// <summary>
        /// Allows ScrumMaster's only to update the story points
        /// of a User Story
        /// </summary>
        public ICommand UpdateStoryCommand
        {
            get
            {
                return new RelayCommand(param =>
                {
                    try
                    {
                        UserStory userStory = ((UserStory)param);
                        _client.UpdateUserStory(userStory.Project.Id, userStory.Id, userStory);

                        MessageBox.Show("User story updated successfully!");
                    }
                    catch (RestResponseErrorException ex)
                    {
                        MessageBoxUtil.ShowErrorBox(ex.Message);
                    }
                });
            }
        }

        #endregion
<<<<<<< HEAD
=======

        #region drag & drop functionality

        /// <summary>
        /// Method to run on mouse left click
        /// Checks to see if a list box item was clicked 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        public void MouseLeftDown(object sender, MouseButtonEventArgs e)
        {
            if (sender is ListBoxItem && Permissions.ProductOwner)
            {
                ListBoxItem draggedItem = sender as ListBoxItem;
                DragDrop.DoDragDrop(draggedItem, draggedItem.DataContext, DragDropEffects.Move);
                draggedItem.IsSelected = true;
            }
        }

        #endregion      
>>>>>>> 86aded57332ffcd9aee3c9e5c6b20836cf241c06
    }


}
